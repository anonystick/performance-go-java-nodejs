package main

import (
	"fmt"
	"sync"
	"sync/atomic"
	"time"
)

func doWork(id int, wg *sync.WaitGroup, counter *int32) {
	defer wg.Done()
	// Giả lập công việc
	atomic.AddInt32(counter, 1)
}

func main() {
	var wg sync.WaitGroup
	var counter int32 = 0
	numGoroutines := 100000000

	start := time.Now() // Bắt đầu đo thời gian

	wg.Add(numGoroutines)

	for i := 0; i < numGoroutines; i++ {
		go doWork(i, &wg, &counter)
	}

	wg.Wait()

	elapsed := time.Since(start) // Tính toán thời gian đã trôi qua
	fmt.Printf("Finished all goroutines. Total completed: %d\n", counter)
	fmt.Printf("Execution Time: %s\n", elapsed)
}
