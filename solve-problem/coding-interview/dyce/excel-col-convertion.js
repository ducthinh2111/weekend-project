(function(){
    console.log("__ loaded!");
    function toIndex(name) {
        var idx = 0;
        var len = (""+name).length;
        var preG = 0;
        for (var i=1; i<len; i++)
            preG += Math.pow(26, i);
        idx += preG;
        for (var i=0; i < len; i++) {
            var grade = len - i;
            var d = name.charCodeAt(i) - 65;
            idx += Math.pow(26, grade-1) * d;
        }
        //console.log("name2idx:", name, idx);
        return idx;
    }
    toIndex("A"); //0
    toIndex("AA"); //26
    toIndex("AAA"); //702
    toIndex("Z"); //25
    toIndex("ZZ"); //701
    toIndex("ZZZ"); //18277
    toIndex("U"); //20
    toIndex("TI"); //528
    toIndex("NYQ"); //10130
    toIndex("HALA"); //141596
    toIndex("YQHOW"); //11729012

    function toName(idx) {
        var name = "";
        var bdry = 26;
        var grade = 1;
        while (idx >= bdry)
            bdry += Math.pow(26, ++grade)
        for (var i=grade-1; i>=0; i++) {
            var gstep = Math.pow(26, i);
            var w = 1;
            var l = 0;
            while ((bdry-w*gstep) > idx) {
                console.log("on "+idx+": bdry/w/low", bdry, w, bdry-w*gstep);
                if (l++ > 30) break;
                w++;
            }
            var c = 26 - w;
            console.log("found 1st letter", idx, 65+c);
            break;
        }
        console.log("idx2name:", idx, name);
        return name;
    }
    toName(0); //A
    toName(26); //AA
    toName(702); //AAA
    toName(25); //Z
    toName(701); //ZZ
    toName(18277); //ZZZ
    toName(20); //U
    toName(528); //TI
    toName(10130); //NYQ
    toName(141596); //HALA
    toName(11729012); //YQHOW
})();
