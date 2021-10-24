package com.crio.qcal;
import com.crio.qcalc.*;


//import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class QcalApplication {

	public static void main(String[] args) {
		//SpringApplication.run(QcalApplication.class, args);
		StandardCalculator calc = new StandardCalculator();
		calc.add(3,2);
		System.out.println(calc.getResult());
		calc.subtract(10,2);
		System.out.println(calc.getResult());

	}

}
