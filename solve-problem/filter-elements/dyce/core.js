(function(){
    var inp = "3\n"
            + "9 2\n"
            + "4 5 2 5 4 3 1 3 4\n"
            + "9 4\n"
            + "4 5 2 5 4 3 1 3 4\n"
            + "10 2\n"
            + "5 4 3 2 1 1 2 3 4 5\n";
    /**
4 5 3
-1
5 4 3 2 1
     */
    var lines = inp.split("\n");
    var cases = +lines[0];
    for (var c = 0; c < cases; c++) {
        var ai = c*2+1;
        var bi = ai+1;
        var k = +lines[ai].split(" ")[1];
        console.log(roll(lines[bi], k));
    }
    function roll(str, k) {
        var arr = str.split(' ');
        var m = {};
        var seq = [];
        for (var i = 0; i < arr.length; i++) {
            var v = +arr[i];
            if (m[v]) {
                m[v] = m[v] + 1;
            } else {
                m[v] = 1;
                seq.push(v);
            }
        }
        var ret = [];
        for (var j = 0; j < seq.length; j++)
            if (m[seq[j]] >= k)
                ret.push(seq[j]);
        return ret.length ? ret : [-1];
    }
})();
