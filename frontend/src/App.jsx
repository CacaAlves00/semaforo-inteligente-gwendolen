import { useEffect } from 'react'
import { useState } from 'react'
import './App.css'
import Semaphore from './components/Semaphore'

function App() {
  const [clock, setClock] = useState({ clock1: 0, clock2: 0 })

  useEffect(() => {
    const clockTimeout = setTimeout(() => {
      if (clock.clock1 > 0) decrementClock('clock1')
      else if (clock.clock2 > 0) decrementClock('clock2')
      else fetchClock()
    }, 950)

    console.log(clock)

    return () => {
      clearTimeout(clockTimeout)
    }
  }, [clock])

  function fetchClock() {
    fetch('http://localhost:3000/get-times', {
      method: 'GET',
      headers: {
        accept: 'application/json',
      },
    })
      .then((response) => {
        return response.json()
      })
      .then((data) => {
        setClock({ clock1: data.clock1Time, clock2: data.clock2Time })
      })
      .catch((err) => {
        // only the force update; this way, it'll fetch the data again
        decrementClock('clock2') 
        
        console.log(err)
      })
  }

  function decrementClock(clock2Decrement) {
    setClock((state) => {
      const newState = { ...state }
      newState[clock2Decrement] = newState[clock2Decrement] - 1

      return newState
    })
  }

  if (clock.clock1 <= 0 && clock.clock2 <= 0) return <h2>Loading...</h2>

  return (
    <div className='App'>
      <Semaphore id={1} isItsTime={clock.clock1 > 0} />
      <Semaphore id={2} isItsTime={clock.clock1 <= 0 && clock.clock2 > 0} />
    </div>
  )
}

export default App
