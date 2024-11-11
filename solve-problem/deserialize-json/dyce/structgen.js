(function(){
	var inc = 0;
	var verbose = 0;
	var depth = rand();
	var root = {};
	var rack = [root];
	verbose && console.log("scan depth: ", depth);
	for (var i = 0; i < depth; i++) {
		verbose && console.log("scan rack: ", rack.length);
		var sub = [];
		for (var j = 0; j < rack.length; j++) {
			var w = rand();
			var b = rack[j];
			var flat = true;
			verbose && console.log("scan width: ", w);
			for (var k = 0; k < w; k++) {
				var l = genValue(i == depth - 1);
				var f = genName();
				b[f] = l;
				verbose && console.log("gen: ", f);
				if (typeof l == "object") {
					flat = false;
					verbose && console.log("to rack: ", f);
					sub.push(l);
				}
			}
			if (flat && i != depth - 1) {
				verbose && console.log("de-flat");
				var l = {};
				var f = genName();
				b[f] = l;
				verbose && console.log("gen: ", f);
				verbose && console.log("to rack: ", f);
				sub.push(l);
			}
		}
		rack = sub;
	}
	console.log(JSON.stringify(root, null, 4));
	function rand() {
		return Math.floor(Math.random()*3) + 1;
	}
	function genValue(leaf) {
		inc++;
		switch(Math.floor(Math.random()*5)) {
			case 0:
				return leaf ? inc : {};
			case 1:
				return inc;
			case 2:
				return ""+inc;
			case 3:
				return inc%2==0;
			default:
				return Date.now();
		}
	}
	function genName() {
		initName();
		return window.names.pop();
	}
	function initName() {
		if (window.names)
			return;
		var txt = window.raw.replace(/[^a-zA-Z ]/g, "");
		var tarr = txt.split(" ");
		var map = {};
		for (var i = 0; i < tarr.length; i++) {
			var w = tarr[i].replace(/ /g, "").toLowerCase();
			if (w.length > 1)
				map[w] = "";
		}
		var arr = Object.keys(map);
		for (var i = 0; i < arr.length; i++) {
			var idx = Math.floor(Math.random()*arr.length);
			var tmp = arr[idx];
			arr[idx] = arr[i];
			arr[i] = tmp;
		}
		window.names = arr;
	}
})()
