--
-- Created by IntelliJ IDEA.
-- Description: 基于时间窗口限流的Lua脚本
-- User: luocong
-- Date: 2020/10/19 10:19
--

-- 限流的key
local limitKey = KEYS[1]
-- 限流大小
local limitCount = tonumber(ARGV[1])
-- key过期时间
local limitPeriod = ARGV[2]

-- 获取当前流量大小
local currCount = redis.call('get', limitKey)

if currCount and tonumber(currCount) + 1 > limitCount then
    return false
end

currCount = redis.call('incr', limitKey)
if tonumber(currCount) == 1 then
    redis.call('expire', limitKey, limitPeriod)
end
return true