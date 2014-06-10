package zimek.zimek.sparkrest;

/**
 * Hello world!
 *
 */
public class RestServiceApp 
{
    public static void main( String[] args )
    {
        new RestUserController(new UserService());
    }
}
