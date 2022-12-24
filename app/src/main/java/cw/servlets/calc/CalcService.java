package cw.servlets.calc;
public class CalcService {
  public CalcResponse calculate(CalcRequest request){
    int result = 0;
    switch (request.getOperation()){
      case "+":
        result = request.getParam1() + request.getParam2();
        break;
      case "-":
        result = request.getParam1() - request.getParam2();
        break;
      case "*":
        result = request.getParam1() * request.getParam2();
        break;
      case "/":
        result = request.getParam1() / request.getParam2();
        break;
    }
    CalcResponse calcResponse = new CalcResponse();
    calcResponse.setRequest(request);
    calcResponse.setResult(result);
    return calcResponse;
  }
}
