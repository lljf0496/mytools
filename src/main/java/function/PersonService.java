package function;

@FunctionalInterface
public interface PersonService<T,R> {
	
	R excutePerson(T arg1,T arg2);

}
