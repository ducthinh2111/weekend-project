
#[derive(Debug, Clone, Copy)]
struct Vec3 {
    x: f32,
    y: f32,
    z: f32
}

impl Vec3 {
    fn add(v1: &Vec3, v2: &Vec3) -> Vec3 {
        Vec3 {
            x: v1.x + v2.x,
            y: v1.y + v2.y,
            z: v1.z + v2.z
        }
    }
    
    fn subtract(v1: &Vec3, v2: &Vec3) -> Vec3 {
        Vec3 {
            x: v1.x - v2.x,
            y: v1.y - v2.y,
            z: v1.z - v2.z
        }
    }
    
    fn dot(v1: &Vec3, v2: &Vec3) -> f32 {
        v1.x * v2.x + v1.y * v2.y + v1.z + v2.z
    }
    
    fn scale(v: &Vec3, scala: f32) -> Vec3 {
        Vec3 {
            x: v.x * scala,
            y: v.y * scala,
            z: v.z * scala
        }
    }
}

#[derive(Debug, Clone, Copy)]
struct Ray {
    position: Vec3,
    direction: Vec3
}

#[derive(Debug, Clone, Copy)]
struct Cube {
    vertices: [Vec3; 8]
}

#[derive(Debug, Clone, Copy)]
struct Screen {
    top_left: Vec3,
    normal: Vec3,
    width: f32,
    height: f32
}



//        z
//        |
//        |
//        |
//        |
//        0 - - - - - x
//       /
//      /
//     /
//    /
//   y
fn main() {
    let pin_hole = Vec3 { x: 2.0, y: 2.0, z: 2.0 };
    let focal_length = 3.0;
    let screen_width = 3.0;
    let screen_height = 3.0;
    let screen_point_top_left = Vec3 {
        x: pin_hole.x - screen_width / 2.0,
        y: pin_hole.y + focal_length,
        z: pin_hole.z + screen_height / 2.0
    };
    let screen_normal = Vec3 {
        x: screen_point_top_left.x,
        y: screen_point_top_left.y + 1.0,
        z: screen_point_top_left.z,
    };
    let screen = Screen {
        top_left: screen_point_top_left,
        normal: screen_normal,
        width: screen_width,
        height: screen_height
    };

    let cube_vertices = create_cube();
    for vertex in cube_vertices {
        let pin_hole_to_vertex = Vec3::subtract(&vertex, &pin_hole);
        let ray = Ray {
            position: pin_hole.clone(),
            direction: pin_hole_to_vertex
        };
        let intersection_scalar = find_intersection(&screen, &ray);
        let intersection = Vec3::add(&ray.position, &Vec3::scale(&ray.direction, intersection_scalar));
    }
}

fn find_intersection(screen: &Screen, ray: &Ray) -> f32 {
    (Vec3::dot(&ray.position, &screen.normal) - Vec3::dot(&screen.point, &screen.normal)) / Vec3::dot(&ray.direction, &screen.normal)
}



//        z
//        |
//        |
//      4 ------ 3
//      | |      |
//      | 0 - - -|- - x
//      |/       |
//      1 ------ 2
//     /
//    /
//   y
// ***
// Vertex 5, 6, 7, 8 has the same order
fn create_cube() -> [Vec3; 8] {
    let vertex1 = Vec3 { x: 0.0, y: 10.0, z: 0.0 };
    let vertex2 = Vec3 { x: 4.0, y: 10.0, z: 0.0 };
    let vertex3 = Vec3 { x: 4.0, y: 10.0, z: 4.0 };
    let vertex4 = Vec3 { x: 0.0, y: 10.0, z: 4.0 };
    let vertex5 = Vec3 { x: 0.0, y: 14.0, z: 0.0 };
    let vertex6 = Vec3 { x: 4.0, y: 14.0, z: 0.0 };
    let vertex7 = Vec3 { x: 4.0, y: 14.0, z: 4.0 };
    let vertex8 = Vec3 { x: 0.0, y: 14.0, z: 4.0 };
    
    [
        vertex1, vertex2, vertex3, vertex4,
        vertex5, vertex6, vertex7, vertex8
    ]
}
