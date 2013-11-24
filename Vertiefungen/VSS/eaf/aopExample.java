@Aspect
@Component // add <component-scan>
public class TracingAnnotations {
	@Autowired
	private Statistic statistic;
	
	//execution(...) = point cut. Or simply what method name pattern
	//it should be used upon.
	@Before("execution(* edu.GreetingService.say*())")
	public void trace() {
		// do something with the statistic bean
	}
}