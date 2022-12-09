const fs = require('fs').promises

async function saveTimes(clock1Time, clock2Time) {
    await fs.writeFile(
        './times.txt',
        JSON.stringify({ clock1Time, clock2Time }),
        'utf-8'
    )
}

async function getTimes() {
    const times = JSON.parse(
        await fs.readFile('times.txt', 'utf-8')
    )
    return times
}

function startTimer(time) {
    return new Promise(async (resolve, reject) => {
        let timer = setTimeout(() => {
            resolve()
        }, time * 1000)
    })
}

module.exports = {
    saveTimes, getTimes, startTimer
}