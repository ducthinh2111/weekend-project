(function(){
    var raw = ""
        + "A\n"
        + "5\n"
        + "A D\n"
        + "A F\n"
        + "F K\n"
        + "F X\n"
        + "D X"
        ;
    var inp = read(raw);
    console.log(comseq(inp.x, inp.deps));

    function read(s) {
        var lines = s.split("\n");
        var out = {x: lines[0], deps: []};
        var len = lines.length;
        for (var i = 2; i < len; i++)
            out.deps.push(lines[i].split(" "));
        return out
    }

    function comseq(x, deps) {
        var m = {};
        var len = deps.length;
        for (var i = 0; i < len; i++) {
            var l = deps[i][0];
            var r = deps[i][1];
            var rn = m[r];
            if (!rn) {
                rn = {name: r, needs:[]};
                m[r] = rn;
            }
            var ln = m[l];
            if (!ln) {
                ln = {name: l, needs:[]};
                m[l] = ln;
            }
            ln.needs.push(rn);
        }
        var seq = flat(m[x]);
        seq = prune(seq);
        console.log(seq.join(" "));
    }

    function flat(t) {
        var f = [];
        if (!t)
            return f;
        var len = t.needs.length;
        for (var i = 0; i < len; i++)
            f = f.concat(flat(t.needs[i]))
        f.push(t.name);
        return f;
    }

    function prune(s) {
        var m = {};
        var len = s.length;
        var out = [];
        for (var i = 0; i < len; i++)
            if (!m[s[i]])
                out.push( m[s[i]] = s[i] );
        return out;
    }
})()
