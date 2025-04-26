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
        console.log("name2idx:", name, idx);
        return idx;
    }
    toIndex("A"); //0
    toIndex("AA"); //26
    toIndex("AAA"); //702
    toIndex("U"); //20
    toIndex("TI"); //528
    toIndex("NYQ"); //10130
    toIndex("HALA"); //141596
    toIndex("YQHOW"); //11729012

    function toName(idx) {
        var name = "";
        // TODO
        console.log("idx2name:", idx, name);
        return name;
    }
    toName(0); //A
    toName(26); //AA
    toName(702); //AAA
    toName(20); //U
    toName(528); //TI
    toName(10130); //NYQ
    toName(141596); //HALA
    toName(11729012); //YQHOW
})();
