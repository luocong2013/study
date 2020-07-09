--
-- Created by IntelliJ IDEA.
-- Description: redis + lua 脚本 基于时间窗口限流
-- User: 01396453
-- Date: 2020/7/8 16:12
--

-- 获取方法签名特征
local limitKey = KEYS[1]

-- 调用脚本传入的限流大小
local limitCount = tonumber(ARGV[1])

-- key过期时间
local limitPeriod = ARGV[2]

-- 获取当前流量大小
local count = redis.call('get', limitKey)

redis.log(redis.LOG_NOTICE, 'key is', limitKey)
redis.log(redis.LOG_NOTICE, 'value is', count)

if count and tonumber(count) + 1 > limitCount then
    return false
end

count = redis.call('incr', limitKey)
if tonumber(count) == 1 then
    redis.call('expire', limitKey, limitPeriod)
end
return true

