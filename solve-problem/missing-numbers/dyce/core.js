(function(){
	function findMissingNum(inp) {
		var a = inp[0];
		var b = inp[1];
		var low = Math.min(a[0], b[0]) - 100;
		var high = Math.max(a[0], b[0]) + 100;
		var gap = high - low;
		var buf = [];//new int[gap+1]
		var d = low;
		if (low > 0)
			d *= -1;
		for (var i = 0; i <= gap; i++)
			buf[i] = 0;
		for (var i = 0; i < a.length; i++)
			buf[a[i]+d]++;
		for (var i = 0; i < b.length; i++)
			buf[b[i]+d]--;
		var out = [];
		for (var i = 0; i <= gap; i++)
			if (buf[i] < 0)
				out.push(i-d);
		return out;
	}
	var itag = document.createElement('textarea');
	document.body.appendChild(itag);
	var btag = document.createElement('button');
	btag.innerText = 'run';
	document.body.appendChild(btag);
	btag.addEventListener('click', function(){
		var be = Date.now()
		printArr( findMissingNum( read() ) );
		var taken = Date.now() - be;
		console.log("process take: (s)", taken);
	});
	function read() {
		var raw = itag.value;
		var lines = raw.split('\n');
		var la = lines[1].split(' ');
		var lb = lines[3].split(' ');
		for (var i = 0; i < la.length; i++)
			la[i] = +(la[i])
		for (var i = 0; i < lb.length; i++)
			lb[i] = +(lb[i])
		return [la, lb];
	}
	function printArr(arr) {
		console.log(arr.join(' '));
	}
})();
