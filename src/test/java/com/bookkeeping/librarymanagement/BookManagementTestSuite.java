package com.bookkeeping.librarymanagement;

import org.junit.runner.JUnitCore;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
		com.bookkeeping.librarymanagement.controller.BookControllerTest.class,
		com.bookkeeping.librarymanagement.service.BookServiceTest.class

})
public class BookManagementTestSuite {
	public static void main(String[] args) {
		JUnitCore.runClasses(new Class[] { BookManagementTestSuite.class });
	}

}
