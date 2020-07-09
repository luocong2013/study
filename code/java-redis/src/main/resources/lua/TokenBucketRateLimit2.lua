--
-- Created by IntelliJ IDEA.
-- Description: redis + lua 脚本 基于令牌桶限流
-- User: 01396453
-- Date: 2020/7/9 9:32
--

--- key，即redis中的key
local key = KEYS[1]
--- 请求令牌数量
local permits = ARGV[1]
--- 桶内令牌最大数量
local max_permits = ARGV[1]
--- 令牌放置速度 (多少个/秒)
local rate = ARGV[1]

return acquire(key, permits, currentTimeMillis(), max_permits, rate)


--- @param key 令牌的唯一标识
--- @param permits  请求令牌数量
--- @param curr_mill_second 当前时间
--- 0 没有令牌桶配置；-1 表示取令牌失败，也就是桶里没有令牌；1 表示取令牌成功
function acquire(key, permits, curr_mill_second, c_max_permits, c_rate)
    local local_key =  key --- 令牌桶key ,使用 .. 进行字符串连接
    if tonumber(redis.pcall("EXISTS", local_key)) < 1 then --- 未配置令牌桶
        if not initTokenBucket(local_key, c_max_permits, c_rate) then
            return false
        end
    end

    --- 令牌桶内数据：
    --- last_mill_second  最后一次放入令牌时间
    --- curr_permits  当前桶内令牌
    --- max_permits   桶内令牌最大数量
    --- rate  令牌放置速度 (单位：个/秒)
    local rate_limit_info = redis.pcall("HMGET", local_key, "last_mill_second", "curr_permits", "max_permits", "rate")
    local last_mill_second = rate_limit_info[1]
    local curr_permits = tonumber(rate_limit_info[2])
    local max_permits = tonumber(rate_limit_info[3])
    local rate = rate_limit_info[4]

    --- 标识没有配置令牌桶
    if type(max_permits) == 'boolean' or max_permits == nil then
        if not initTokenBucketMaxPermits(key, c_max_permits) then
            return false
        end
    end
    --- 若令牌桶参数没有配置，则返回0
    if type(rate) == 'boolean' or rate == nil then
        if not initTokenBucketRate(key, c_rate) then
            return false
        end
    end

    local local_curr_permits = max_permits;

    --- 令牌桶刚刚创建，上一次获取令牌的毫秒数为空
    --- 根据和上一次向桶里添加令牌的时间和当前时间差，触发式往桶里添加令牌，并且更新上一次向桶里添加令牌的时间
    --- 如果向桶里添加的令牌数不足一个，则不更新上一次向桶里添加令牌的时间
    --- ~=号在Lua脚本的含义就是不等于!=
    if (type(last_mill_second) ~= 'boolean' and last_mill_second ~= nil) then
        if (curr_mill_second - last_mill_second < 0) then
            return false
        end
        --- 生成令牌操作
        local reverse_permits = math.floor(((curr_mill_second - last_mill_second) / 1000) * rate) --- 最关键代码：根据时间差计算令牌数量并匀速的放入令牌
        local expect_curr_permits = reverse_permits + curr_permits
        local_curr_permits = math.min(expect_curr_permits, max_permits) --- 如果期望令牌数大于桶容量，则设为桶容量
        --- 大于0表示这段时间产生令牌，则更新最新令牌放入时间
        if (reverse_permits > 0) then
            redis.pcall("HSET", local_key, "last_mill_second", curr_mill_second)
        end
    else
        redis.pcall("HSET", local_key, "last_mill_second", curr_mill_second)
    end
    --- 取出令牌操作
    if (local_curr_permits - permits >= 0) then
        redis.pcall("HSET", local_key, "curr_permits", local_curr_permits - permits)
        return true
    else
        redis.pcall("HSET", local_key, "curr_permits", local_curr_permits)
        return false
    end
end


--- 初始化令牌桶
local function initTokenBucket(key, max_permits, rate)
    if(key == nil or string.len(key) < 1) then
        return false
    end
    return initTokenBucketMaxPermits(key, max_permits) and initTokenBucketRate(key, rate)
end

--- 初始化桶内令牌最大数量
local function initTokenBucketMaxPermits(key, max_permits)
    if (key == nil or string.len(key) < 1) then
        return false
    end
    local local_max_permits = 100
    if(tonumber(max_permits) > 0) then
        local_max_permits = max_permits
    end
    redis.pcall("HSET", key, "max_permits", local_max_permits)
    return true
end

--- 初始化令牌放置速度 (单位：个/秒)
local function initTokenBucketRate(key, rate)
    if(key == nil or string.len(key) < 1) then
        return false
    end
    local local_rate = 100
    if(tonumber(rate) > 0) then
        local_rate = rate
    end
    redis.pcall("HSET", key, "rate", local_rate)
    return true
end

--- 获取当前时间，单节点获取，避免集群模式下(无论业务系统集群，还是redis集群)获取的时间不同，导致桶不匀速
local function currentTimeMillis()
    local times = redis.pcall("TIME")
    return tonumber(times[1]) * 1000 + tonumber(times[2]) / 1000
end
