package com.epam.training.gen.ai.semantic.plugins;

import com.microsoft.semantickernel.semanticfunctions.annotations.DefineKernelFunction;
import com.microsoft.semantickernel.semanticfunctions.annotations.KernelFunctionParameter;

import lombok.extern.slf4j.Slf4j;

/**
 * A simple plugin that defines a kernel function for performing a basic action
 * on data.
 * <p>
 * This plugin exposes a method to be invoked by the kernel, which logs and
 * returns the input query.
 */
@Slf4j
public class CalculatorPlugin {
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(CalculatorPlugin.class);

	@DefineKernelFunction(name = "additionCalculator")
	public double add(
			@KernelFunctionParameter(description = "first number", name = "firstNumber") double firstNumber
			, @KernelFunctionParameter(description = "second number", name = "secondNumber") double secondNumber) {
		log.info("add");
	 return firstNumber + secondNumber;
	}
	
	@DefineKernelFunction(name = "substractionCalculator")
	public double substract(
			@KernelFunctionParameter(description = "first number", name = "firstNumber") double firstNumber
			, @KernelFunctionParameter(description = "second number", name = "secondNumber") double secondNumber) {
		log.info("substract");
		return firstNumber - secondNumber;
	}
	
	@DefineKernelFunction(name = "multiplicationCalculator")
	public double multiply(
			@KernelFunctionParameter(description = "first number", name = "firstNumber") double firstNumber
			, @KernelFunctionParameter(description = "second number", name = "secondNumber") double secondNumber) {
		log.info("multiply");
		return firstNumber * secondNumber;
	}
	
	@DefineKernelFunction(name = "divisionCalculator")
	public double divide(
			@KernelFunctionParameter(description = "first number", name = "firstNumber") double firstNumber
			, @KernelFunctionParameter(description = "second number", name = "secondNumber") double secondNumber) {
		log.info("divide");
		return firstNumber / secondNumber;
	}
}
