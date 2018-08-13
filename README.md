# Contacts
A simple demo app, displaying the list of contacts from device and allowing to view details of a selected one.

For such simple functionality it may look over-engineered, but in this case it was intentional - my goal was to demonstrate a way to write the code that any (imaginary) future requirements could be easily added and the domain code is concise and readable.

It was implemented using Model-View-intent (MVI) approach and is using the following cool technologies:
* Android Jetpack:
	* Lifecycle
	* ViewModel
	* Navigation
* RxJava
* Dependency Injection: Koin
* Testing: Spek

It supports runtime permission checking for contacts access.
