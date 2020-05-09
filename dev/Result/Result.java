package Result;

public class Result<T> {
    ResultTags myTag;
    T value;
    String my_message;
    private Result(ResultTags tag,String message)
    {
        myTag=tag;
        this.my_message=message;
    }

    public static Result makeOk(String message)
    {
        return new Result(ResultTags.OK,message);
    }

    public static Result makeFailure(String message)
    {
        return new Result(ResultTags.Failure,message);
    }

    public boolean isOk() {
        return myTag==ResultTags.OK;
    }

    public boolean isFailure() {
        return myTag==ResultTags.Failure;
    }

    public String getMessage() {
        return my_message;
    }
}

enum ResultTags
{
    OK,
    Failure
}