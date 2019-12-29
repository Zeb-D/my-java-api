---
--- Created by Zeb灬D.
--- DateTime: 2017/11/11 10:10
---

local function max(a ,b )
    local temp = nil;
    if a > b then
        temp = a;
    else
        temp = b;
    end
    return temp;
end

local function swap(a,b)
    return b,a
end


--
local function run(x,y)
    print('run',x,y)
end

local function attack( targetId)
    print('targetId',targetId)
end

local function do_action(method,...)
    local args = {...} or {}
    method( unpack(args,1,table.maxn(args)))
end

do_action(run ,1,2);
do_action(attack,1111)

--
local fp = require("com.yd.lua.my")
fp.greeting();

--
print(string.char(96,97,98))
print(string.char())

--
local square = require("com.yd.lua.square")
local s1 = square:new(1,2)
print(s1:get_square())
print(s1:get_circumference())