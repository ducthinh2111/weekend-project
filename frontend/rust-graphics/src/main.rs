use softbuffer::{Context, Surface};
use std::num::NonZeroU32;
use std::rc::Rc;
use winit::application::ApplicationHandler;
use winit::event::{ElementState, WindowEvent};
use winit::event_loop::{ActiveEventLoop, ControlFlow, EventLoop};
use winit::window::{Window, WindowAttributes, WindowId};

#[derive(Default)]
struct App {
    window: Option<Rc<Window>>,
    context: Option<Context<Rc<Window>>>,
    surface: Option<Surface<Rc<Window>, Rc<Window>>>
}

impl ApplicationHandler for App {
    fn resumed(&mut self, event_loop: &ActiveEventLoop) {
        let window = Rc::new(event_loop.create_window(WindowAttributes::default()).unwrap());
        let context = Context::new(window.clone()).unwrap();
        self.surface = Some(Surface::new(&context, window.clone()).unwrap());
        self.context = Some(context);
        self.window = Some(window);
    }

    fn window_event(&mut self, event_loop: &ActiveEventLoop, window_id: WindowId, event: WindowEvent) {
        match event {
            WindowEvent::CloseRequested => {
                event_loop.exit();
            },
            WindowEvent::RedrawRequested => {
                let window = self.window.as_mut().unwrap();
                let size = window.inner_size();
                if let (Some(width), Some(height)) = (NonZeroU32::new(size.width), NonZeroU32::new(size.height)) {
                    let surface = self.surface.as_mut().unwrap();
                    let mut buffer = surface.buffer_mut().unwrap();
                    println!("Width: {} Height {}", width, height);
                    for y in 0..height.get() {
                        for x in 0..width.get() {
                            let red = 0;
                            let mut green = 0;
                            let blue = 0;

                            if x >= width.get() / 3 &&
                                x <= (width.get() * 2) / 3 &&
                                y >= height.get() / 3 &&
                                y <= (height.get() * 2) / 3 {
                                green = 255;
                            }

                            let index = y as usize * width.get() as usize + x as usize;
                            buffer[index] = blue | (green << 8) | (red << 16)
                        }
                    }
                    buffer.present().unwrap();
                }
            },
            WindowEvent::Resized(size) => {
                let surface = self.surface.as_mut().unwrap();

                if let (Some(width), Some(height)) =
                    (NonZeroU32::new(size.width), NonZeroU32::new(size.height))
                {
                    surface.resize(width, height).unwrap();
                }
            },
            WindowEvent::MouseInput { device_id, state, button } => {
                match state {
                    ElementState::Pressed => println!("Pressed"),
                    ElementState::Released => println!("Released")
                }
            }
            _ => ()
        }
    }
}

fn main() {
    let event_loop = EventLoop::new().unwrap();
    event_loop.set_control_flow(ControlFlow::Wait);
    let mut app = App::default();
    event_loop.run_app(&mut app).expect("Something went wrong");
}
