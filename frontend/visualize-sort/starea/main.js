const canvas = document.getElementById("canvas");
const canvasWidth = canvas.width;
const canvasHeight = canvas.height;
const ctx = canvas.getContext("2d");

const num_arr = random_num_arr();
const rect_width = canvasWidth / num_arr.length;

// Find largest number
let largest = num_arr[0];
for (let i = 1; i < num_arr.length; i++) {
    if (num_arr[i] > largest) {
        largest = num_arr[i];
    }
}

run_sort();

async function run_sort() {
    for (let i = 0; i < num_arr.length - 1; i++) {
        swapped = false;
        for (let j = 0; j < num_arr.length - i - 1; j++) {
            if (num_arr[j] > num_arr[j + 1]) {  
                // Swap arr[j] and arr[j+1]
                temp = num_arr[j];
                num_arr[j] = num_arr[j + 1];
                num_arr[j + 1] = temp;
                swapped = true;
                await new Promise((resolve) => { setTimeout(() => resolve(), 0.5)});
                render();
            }
        }

        // If no two elements were
        // swapped by inner loop, then break
        if (swapped == false) break;
    }
}

function render() {
    ctx.clearRect(0, 0, canvasWidth, canvasHeight);
    for (let i = 0; i < num_arr.length; i++) {
        ctx.beginPath();
        ctx.fillStyle = "white";
        const rect_height = (num_arr[i] / largest) * canvasHeight;
        const x = i * rect_width;
        const y = canvasHeight - rect_height;
        ctx.rect(x, y, rect_width, rect_height); 
        ctx.fill();
    }
}

function random_num_arr() {
    let arr_length = 100;
    let max_num = 50;
    let num_arr = [];
    for (let i = 0; i < arr_length; i++) {
        num_arr.push(Math.floor(Math.random() * max_num))
    }

    return num_arr;
}