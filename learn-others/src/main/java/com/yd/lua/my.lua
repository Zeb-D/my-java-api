---
--- Created by Zeb灬D.
--- DateTime: 2017/11/11 10:43
---

local foo={}

local function getName()
    return "Lucy"
end

function foo.greeting()
    print("hello "..getName())
end

return foo