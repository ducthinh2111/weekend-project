$(document).ready(function() {
    $("#m-go").click(function() {
        displayArray($("#m-array").val());
    });
});

function displayArray(arr) {
    const a = arr.split(",").map(Number);
    const colors = new Map();
    let div = "";
    for (let i = 0; i < a.length; i++) {
        const t = a[i];
        let color = colors.get(t);
        if (color == undefined) {
            color = getRandomColor(colors);
        }
        colors.set(t, color);
        const id = t + "" + i;
        div += "<div class = 'm-item' style='background-color:" + color + "' id ='" + id + "'>" + t + "</div>";
    }
    $("#m-array-out-put").html(div);
    sort(a);
}

async function sort(arr) {
    let min;
    for (let i = 0; i < arr.length - 1; i++) {
        min = i;
        let n = arr[i];
        for (let j = i + 1; j < arr.length; j++) {
            if (arr[j] < arr[min]) {
                min = j;
            }
        }
        if (min != i) {
           await swap(i, min, arr);
        }
    }
}
async function swap(i , j, a) {
    const div1 = $("#" + a[i] + i)[0];
    const div2 = $("#" + a[j] + j)[0];
    const d = j - i;
    let x1 = div2.offsetWidth * d;
    let x2 = - (div1.offsetWidth * d);
    let css1 = "translate("+ x1 +"px, -50px)";
    let css2 = "translate("+ x2 +"px, 50px)";
    div1.style.transition = "2s";
    div2.style.transition = "2s";
    div1.style.transform = css1;
    div2.style.transform = css2;
    await new Promise((a, r) => setTimeout(a, 2000));
    let t = a[i];
    let m = a[j];
    a[i] = m;
    a[j] = t;
    css1 = "translate("+ x1 +"px, 0px)";
    css2 = "translate("+ x2 +"px, 0px)";
    div1.style.transform = css1;
    div2.style.transform = css2;
    div1.id = t + "" + j;
    div2.id = m + "" + i;
    let parent = div1.parentElement;
    await new Promise((a, r) => setTimeout(a, 2000));
    parent.insertBefore(div1, parent.children[j]);
    div1.style.removeProperty("transform");
    parent.insertBefore(div2, parent.children[i]);
    div2.style.removeProperty("transform");
}


function getRandomColor(colors) {
  var letters = '0123456789ABCDEF';
  var color = '#';
  for (var i = 0; i < 6; i++) {
    color += letters[Math.floor(Math.random() * 16)];
  }
  if (Object.values(colors).includes(color)){
    return getRandomColor(colors);
  }
  return color;
}