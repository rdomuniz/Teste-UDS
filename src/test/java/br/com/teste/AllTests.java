package br.com.teste;

import org.junit.runner.RunWith;

import com.googlecode.junittoolbox.SuiteClasses;
import com.googlecode.junittoolbox.WildcardPatternSuite;

@SuiteClasses("**/*Test.class")
@RunWith(WildcardPatternSuite.class)
public class AllTests{}