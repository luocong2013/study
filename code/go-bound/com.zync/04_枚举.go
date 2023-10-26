package main

import "fmt"

func main() {
	// 1、iota 常量自动生成器，每隔一行，自动累加1

	const (
		n1 = iota
		_
		n2 = iota
		n3 = iota
	)
	fmt.Printf("n1 = %v, n2 = %v, n3 = %v\n", n1, n2, n3)

	// 2、iota遇到const 重置为0
	const d = iota
	fmt.Printf("d = %v\n", d)

	// 3、可以只写一个 iota
	const (
		cpp = iota
		java
		python
		golang
	)
	fmt.Printf("cpp = %v, java = %v, python = %v, golang = %v\n", cpp, java, python, golang)

	// 4、如果是同同一行，值都一样
	const (
		i          = iota
		j1, j2, j3 = iota, iota, iota
		k          = iota
	)
	fmt.Printf("i = %v, j1 = %v, j2 = %v, j3 = %v, k = %v\n", i, j1, j2, j3, k)

	const (
		b = 1 << (10 * iota)
		kb
		mb
		gb
		tb
		pb
	)
	fmt.Printf("b = %v, kb = %v, mb = %v, gb = %v, tb = %v, pb = %v\n", b, kb, mb, gb, tb, pb)
}
