--
-- Created by IntelliJ IDEA.
-- Description: redis + lua 脚本 基于令牌桶限流
-- User: 01396453
-- Date: 2020/7/9 9:32
--

--- key，即redis中的key
local key = KEYS[1]
--- 请求令牌数量
local permits = tonumber(ARGV[1])
--- 桶内令牌最大数量
local max_permits = tonumber(ARGV[2])
--- 令牌放置速度 (多少个/秒)
local rate = tonumber(ARGV[3])

--- 开启单命令复制模式
redis.replicate_commands()
local local_key =  key --- 令牌桶key ,使用 .. 进行字符串连接
if tonumber(redis.pcall("EXISTS", local_key)) < 1 then --- 未配置令牌桶
    if local_key == nil or string.len(local_key) < 1 then
        return false
    end
    local local_max_permits = 100
    if max_permits > 0 then
        local_max_permits = max_permits
    end
    redis.pcall("HSET", local_key, "max_permits", local_max_permits)
    local local_rate = 100
    if rate > 0 then
        local_rate = rate
    end
    redis.pcall("HSET", local_key, "rate", local_rate)
end

--- 令牌桶内数据：
--- db_last_mill_second  最后一次放入令牌时间
--- db_curr_permits  当前桶内令牌
--- db_max_permits   桶内令牌最大数量
--- db_rate  令牌放置速度 (单位：个/秒)
local db_rate_limit_info = redis.pcall("HMGET", local_key, "last_mill_second", "curr_permits", "max_permits", "rate")
local db_last_mill_second = tonumber(db_rate_limit_info[1])
local db_curr_permits = tonumber(db_rate_limit_info[2])
local db_max_permits = tonumber(db_rate_limit_info[3])
local db_rate = tonumber(db_rate_limit_info[4])

--- 标识没有配置令牌桶
if type(db_max_permits) == 'boolean' or db_max_permits == nil then
    if local_key == nil or string.len(local_key) < 1 then
        return false
    end
    local local_max_permits = 100
    if max_permits > 0 then
        local_max_permits = max_permits
    end
    redis.pcall("HSET", local_key, "max_permits", local_max_permits)
end
--- 若令牌桶参数没有配置，则返回0
if type(db_rate) == 'boolean' or db_rate == nil then
    if local_key == nil or string.len(local_key) < 1 then
        return false
    end
    local local_rate = 100
    if rate > 0 then
        local_rate = rate
    end
    redis.pcall("HSET", local_key, "rate", local_rate)
end

local local_curr_permits = db_max_permits;
local times = redis.pcall("TIME")
local curr_mill_second = tonumber(times[1] * 1000 + times[2] / 1000)

--- 令牌桶刚刚创建，上一次获取令牌的毫秒数为空
--- 根据和上一次向桶里添加令牌的时间和当前时间差，触发式往桶里添加令牌，并且更新上一次向桶里添加令牌的时间
--- 如果向桶里添加的令牌数不足一个，则不更新上一次向桶里添加令牌的时间
--- ~=号在Lua脚本的含义就是不等于!=
if type(db_last_mill_second) ~= 'boolean' and db_last_mill_second ~= nil then
    if curr_mill_second - db_last_mill_second < 0 then
        return false
    end
    --- 生成令牌操作
    local reverse_permits = math.floor(((curr_mill_second - db_last_mill_second) / 1000) * db_rate) --- 最关键代码：根据时间差计算令牌数量并匀速的放入令牌
    local expect_curr_permits = reverse_permits + db_curr_permits
    local_curr_permits = math.min(expect_curr_permits, db_max_permits) --- 如果期望令牌数大于桶容量，则设为桶容量
    --- 大于0表示这段时间产生令牌，则更新最新令牌放入时间
    if reverse_permits > 0 then
        redis.pcall("HSET", local_key, "last_mill_second", curr_mill_second)
    end
else
    redis.pcall("HSET", local_key, "last_mill_second", curr_mill_second)
end
--- 取出令牌操作
if local_curr_permits - permits >= 0 then
    redis.pcall("HSET", local_key, "curr_permits", local_curr_permits - permits)
    return true
else
    redis.pcall("HSET", local_key, "curr_permits", local_curr_permits)
    return false
end