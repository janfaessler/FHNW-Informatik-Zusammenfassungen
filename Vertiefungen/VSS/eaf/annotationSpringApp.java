public class DecoupledHelloWorldWithSpring {
	public static void main(String[] args) {
		BeanFactory factory = getBeanFactory();
		MessageRenderer mr = (MessageRenderer) factory.getBean("renderer");
		mr.render();
	}
	private static BeanFactory getBeanFactory() {
		return new XmlBeanFactory(new ClassPathResource("helloConfig.xml"));
	}
}	
@Component
public class HelloWorldProvider implements MessageProvider {
	public String getMessage() { return "Hello World"; }
}
@Component
public class StandardOutRenderer implements MessageRenderer {
	@Autowired
	private MessageProvider provider = null;
    public void render() {
		System.out.println(provider.getMessage());
	}
	public void setMessageProvider(MessageProvider mp) {
		provider = mp;
	}
}