abstract class Shape {

	abstract double getPerimeter();

	abstract double getArea();
}

class Triangle extends Shape {

	private final double a;
	private final double b;
	private final double c;

	Triangle(double a, double b, double c) {
		this.a = a;
		this.b = b;
		this.c = c;
	}


	@Override
	double getPerimeter() {
		return a + b + c;
	}

	@Override
	double getArea() {
		return Math.sqrt(
				(getPerimeter() / 2)
						* ((getPerimeter() / 2) - a)
						* ((getPerimeter() / 2) - b)
						* ((getPerimeter() / 2) - c)
		);
	}
}

class Rectangle extends Shape {
	private final double a;
	private final double b;

	Rectangle(double a, double b) {
		this.a = a;
		this.b = b;
	}

	@Override
	double getPerimeter() {
		return (a + b) * 2;
	}

	@Override
	double getArea() {
		return a * b;
	}
}

class Circle extends Shape {
	private final double radius;

	Circle(double radius) {
		this.radius = radius;
	}

	@Override
	double getPerimeter() {
		return 2 * Math.PI * radius;
	}

	@Override
	double getArea() {
		return Math.PI * radius * radius;
	}
}