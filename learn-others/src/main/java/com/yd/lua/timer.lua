---
--- Created by Zeb灬D.
--- DateTime: 2017/11/11 11:33
---
local delay = 5
local handler

handler = function( premature)
    if premature then
        return
    end

    local ok,err = ngx.timer.at(delay,handler)
    if not ok then
        ngx.log(ngx.ERR,"failed to create the timer:",err)
        return
    end
end

local ok, err = ngx.timer.at(delay,handler)