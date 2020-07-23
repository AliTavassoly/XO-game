package xo.model;

public class Packet {
    private String functionName;
    private Object[] args;
    private String authToken;

    public String getFunctionName() {
        return functionName;
    }
    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public Object[] getArgs() {
        return args;
    }
    public void setArgs(Object[] args) {
        this.args = args;
    }

    public String getAuthToken() {
        return authToken;
    }
    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public Packet(){

    }

    public Packet(String functionName, Object[] args){
        this.functionName = functionName;
        this.args = args;
    }
}
