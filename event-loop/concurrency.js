const numTasks = 3000000;
let counter = 0;

async function doWork(id) {
  // Increment counter
  counter++;
}

async function main() {
    console.time('Execution Time'); // Start Timer
  const tasks = [];

  for (let i = 0; i < numTasks; i++) {
    tasks.push(doWork(i));
  }

  await Promise.all(tasks);
  console.timeEnd('Execution Time'); // Stop Timer
  console.log(`Finished all tasks. Total completed: ${counter}`);
}

main().catch(console.error);
