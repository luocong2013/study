package main

import "fmt"

func main() {
	// 常量 在程序运行期间不能改变，常量的声明需要使用 const
	// 不能使用 := 赋值

	const n1 int = 11
	fmt.Println("n1 = ", n1)

	const n2, n3 = 10, "double"
	fmt.Println("n2 = ", n2, ", n3 = ", n3)
	fmt.Printf("n2 type is %T\n", n2)
	fmt.Printf("n3 type is %T\n", n3)

	const (
		num11 int = 88
		num12     = 99
		num13     = "netty"
	)
	fmt.Printf("num11 = %v, num12 = %v, num13 = %v\n", num11, num12, num13)
}
