use std::fs;
use std::env;

fn main() {
    let json_file_path = env::args().nth(1).expect("No JSON file given");
    let data = fs::read_to_string(json_file_path).expect("Unable to read file");
    println!("{}", data);
}
