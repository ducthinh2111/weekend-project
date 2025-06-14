
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

    fn unit(&self) -> Vec3 {
        let vec_length = (self.x.powi(2) + self.y.powi(2) + self.z.powi(2)).sqrt();
        Vec3 {
            x: self.x / vec_length,
            y: self.y / vec_length,
            z: self.z / vec_length
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
    horizontal: Vec3,
    vertical: Vec3,
    horizontal_length: f32,
    vertical_length: f32
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
    let screen_width = 100.0;
    let screen_height = 100.0;
    let screen_point_top_left = Vec3 {
        x: pin_hole.x - screen_width / 2.0,
        y: pin_hole.y + focal_length,
        z: pin_hole.z - screen_height / 2.0
    };

    let direction_determine_vector = Vec3 {
        x: screen_point_top_left.x,
        y: screen_point_top_left.y + 1.0,
        z: screen_point_top_left.z
    };
    let screen_normal = Vec3::subtract(&direction_determine_vector, &screen_point_top_left);

    let direction_determine_vector = Vec3 {
        x: screen_point_top_left.x + 1.0,
        y: screen_point_top_left.y,
        z: screen_point_top_left.z
    };
    let screen_horizontal = Vec3::subtract(&direction_determine_vector, &screen_point_top_left);

    let direction_determine_vector = Vec3 {
        x: screen_point_top_left.x,
        y: screen_point_top_left.y,
        z: screen_point_top_left.z + 1.0
    };
    let screen_vertical = Vec3::subtract(&direction_determine_vector, &screen_point_top_left);

    let screen = Screen {
        top_left: screen_point_top_left,
        normal: screen_normal,
        horizontal: screen_horizontal,
        vertical: screen_vertical,
        horizontal_length: screen_width,
        vertical_length: screen_height
    };

    let cube = create_cube();
    let pixels_width = 300;
    let pixels_height = 300;
    for vertex in cube.vertices {
        let pin_hole_to_vertex = Vec3::subtract(&vertex, &pin_hole);
        let ray = Ray {
            position: pin_hole.clone(),
            direction: pin_hole_to_vertex
        };
        let intersection_scalar = find_intersection(&screen, &ray);
        let intersection = Vec3::add(&ray.position, &Vec3::scale(&ray.direction, intersection_scalar));
        let screen_top_left_to_intersection = Vec3::subtract(&intersection, &screen.top_left);
        let horizontal_projection = Vec3::dot(&screen_top_left_to_intersection, &screen.horizontal.unit());
        let vertical_projection = Vec3::dot(&screen_top_left_to_intersection, &screen.vertical.unit());
        println!("Horizontal projection for vertex x:{} y:{} z:{} is {}", vertex.x, vertex.y, vertex.z, horizontal_projection);
        println!("Vertical projection for vertex x:{} y:{} z:{} is {}", vertex.x, vertex.y, vertex.z, vertical_projection);
    }
    
    
}

fn find_intersection(screen: &Screen, ray: &Ray) -> f32 {
    (Vec3::dot(&ray.position, &screen.normal) - Vec3::dot(&screen.top_left, &screen.normal)) / Vec3::dot(&ray.direction, &screen.normal)
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
fn create_cube() -> Cube {
    let vertex1 = Vec3 { x: 0.0, y: 10.0, z: 0.0 };
    let vertex2 = Vec3 { x: 4.0, y: 10.0, z: 0.0 };
    let vertex3 = Vec3 { x: 4.0, y: 10.0, z: 4.0 };
    let vertex4 = Vec3 { x: 0.0, y: 10.0, z: 4.0 };
    let vertex5 = Vec3 { x: 0.0, y: 14.0, z: 0.0 };
    let vertex6 = Vec3 { x: 4.0, y: 14.0, z: 0.0 };
    let vertex7 = Vec3 { x: 4.0, y: 14.0, z: 4.0 };
    let vertex8 = Vec3 { x: 0.0, y: 14.0, z: 4.0 };

    Cube {
        vertices: [
            vertex1, vertex2, vertex3, vertex4,
            vertex5, vertex6, vertex7, vertex8
        ]
    }
}
