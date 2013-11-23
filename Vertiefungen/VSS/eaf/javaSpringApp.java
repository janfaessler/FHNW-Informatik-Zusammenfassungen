@Configuration
@ComponentScan
public class Application {
    @Bean
    MessageProvider provider() {
        return new HelloWorldProvider();
    }
  public static void main(String[] args) {
      ApplicationContext context = new AnnotationConfigApplicationContext(Application.class);
      MessageRenderer m = context.getBean(StandardOutRenderer.class);
      m.render();
  }
}