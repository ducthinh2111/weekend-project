import { Universe, Cell } from "rust-graphics";
import { memory } from "rust-graphics/rust_graphics_bg.wasm";

const CELL_SIZE = 6;
const GRID_COLOR = "#CCCCCC";
const DEAD_COLOR = "#FFFFFF";
const ALIVE_COLOR = "#000000";


const universe = Universe.new();
const width = universe.width();
const height = universe.height();

const canvas = document.getElementById("rust-graphics-canvas");
canvas.width = (CELL_SIZE + 1) * width + 1;
canvas.height = (CELL_SIZE + 1) * height + 1;
const ctx = canvas.getContext("2d");

const drawGrid = () => {
    ctx.beginPath();
    ctx.strokeStyle = GRID_COLOR;

    for (let i = 0; i <= width; i++) {
        ctx.moveTo(i * (CELL_SIZE + 1) + 0.5, 0)
        ctx.lineTo(i * (CELL_SIZE + 1) + 0.5, (CELL_SIZE + 1) * height + 1)
    }

    for (let i = 0; i <= height; i++) {
        ctx.moveTo(0, i * (CELL_SIZE + 1) + 0.5)
        ctx.lineTo((CELL_SIZE + 1) * height + 1, i * (CELL_SIZE + 1) + 0.5)
    }

    ctx.stroke();
};

const getIndex = (row, column) => {
    return row * width + column;
};

const drawCells = () => {
    const cellsPtr = universe.cells();
    const cells = new Uint8Array(memory.buffer, cellsPtr, width * height);

    ctx.beginPath();

    for (let row = 0; row < height; row++) {
        for (let col = 0; col < width; col++) {
            const idx = getIndex(row, col);

            ctx.fillStyle = cells[idx] === Cell.Dead
                ? DEAD_COLOR
                : ALIVE_COLOR;

            ctx.fillRect(
                col * (CELL_SIZE + 1) + 1,
                row * (CELL_SIZE + 1) + 1,
                CELL_SIZE,
                CELL_SIZE
            );
        }
    }

    ctx.stroke();
}

const renderLoop = () => {
    drawGrid();
    drawCells();
    universe.tick();

    requestAnimationFrame(renderLoop);
}

requestAnimationFrame(renderLoop);


