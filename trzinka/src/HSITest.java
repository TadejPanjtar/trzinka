
public class HSITest {

	public static void main(String[] args) throws Exception {
		String server = "gambee.com";
		if (args.length>0) server=args[0];
		HttpServerInfo si = new HttpServerInfo();
		System.out.println(si.getInfo(server));
	}


}
