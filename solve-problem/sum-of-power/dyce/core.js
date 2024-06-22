(function(){
    function count(x, n) {
        var t = Math.floor(Math.pow(x, (1/n)))
        var can = [];
        for (var i = t; i > 0; i--)
            can.push(Math.pow(i, n));
        var sum = 0;
        for (var i = 0; i < can.length; i++)
            sum += pick(x, can, i);
        return sum;
    }
    function pick(x, arr, idx) {
        if (!arr.length)
            return 0;
        var v = arr[idx];
        if (v == x)
            return 1;
        var next = x-v;
        var can = [];
        for (var i = idx+1; i < arr.length; i++)
            if (arr[i] <= next)
                can.push(arr[i]);
        var sum = 0;
        for (var i = 0; i < can.length; i++)
            sum += pick(next, can, i);
        return sum;
    }
    function test(name, x, n, o) {
        var actual = count(x, n);
        if (actual == o)
            console.log(name + " :: ok");
        else
            console.warn(name + " << given ("+x+","+n+") expected: " + o + " but was: " + actual);
    }
    test("Case 00", 10, 2, 1);
    test("Case 01", 100, 2, 3);
    test("Case 02", 100, 3, 1);
    test("Case 03", 800, 2, 561);
    test("Case 04", 1000, 10, 0);
    test("Case 05", 400, 2, 55);
})();
