
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
    point: Vec3,
    normal: Vec3,
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
    let screen_point = Vec3 {
        x: pin_hole.x,
        y: pin_hole.y + 3.0,
        z: pin_hole.z
    };
    let screen_normal = Vec3 {
        x: screen_point.x,
        y: screen_point.y + 1.0,
        z: screen_point.z,
    };
    let screen = Screen {
        point: screen_point,
        normal: screen_normal
    };

    let cube_vertices = create_cube();
    let mut rays: Vec<Ray> = Vec::new();
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
