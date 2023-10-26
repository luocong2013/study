package main

import "fmt"

// 全局变量：定义在函数外的变量
var n1 = 100
var n2 = 9.7

// 可以一次声明
var (
	n3 = "tom"
	n4 = 3.1415926
)

func main() {
	// ==================第一种================= //
	// 1. 变量的声明
	var num1 int
	// 2. 变量的赋值
	num1 = 6
	// 3. 变量的使用
	fmt.Println("num1 = ", num1)

	// ==================第二种================= //
	// 变量的声明和赋值可以合成一行
	var num2 int = 11
	fmt.Println("num2 = ", num2)

	// ==================第三种================= //
	// 使用默认值
	var num3 int
	fmt.Println("num3 = ", num3)

	// ==================第四种================= //
	// 如果没有写变量的类型，那么会根据=后面的值进行判定变量的类型（自动类型推断）
	var num4 = 3.14
	fmt.Println("num4 = ", num4)

	// ==================第五种================= //
	// 省略 var
	num5 := 14
	fmt.Println("num5 = ", num5)

	// 多变量同时声明
	num6, num7, num8 := 8, 3.18, -10
	fmt.Println("num6 = ", num6, ", num7 = ", num7, ", num8 = ", num8)

	var num9, num10 = "jack", 100
	fmt.Println("num9 = ", num9, ", num10 = ", num10)

	var (
		num11 int = 88
		num12     = 99
		num13     = "netty"
	)
	fmt.Printf("num11 = %v, num12 = %v, num13 = %v\n", num11, num12, num13)

	// 全局变量是使用
	fmt.Printf("n1 = %v, n2 = %v, n3 = %v, n4 = %v\n", n1, n2, n3, n4)
}
