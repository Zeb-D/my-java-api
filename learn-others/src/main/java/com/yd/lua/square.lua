---
--- Created by Zeb灬D.
--- DateTime: 2017/11/11 10:51
---

local _M = {}
_M._VERSION = '1.0'

local mt = { __index = _M}

function _M.new(self,width,height)
    return setmetatable({width= width,height = height},mt)
end

function _M.get_square(self)
    return self.width * self.height
end

function _M.get_circumference(self)
    return (self.width + self.height) *2
end

return _M