import React from 'react'
import { useState, useEffect } from 'react'
import './Semaphore.css'

function Semaphore({ id, isItsTime }) {
  const [style, setStyle] = useState({
    greenLightColor: '#62a87c',
    redLightColor: '#50514F',
  })

  useEffect(() => {
    if (isItsTime)
      setStyle({
        greenLightColor: '#62a87c',
        redLightColor: '#50514F',
      })
    else
      setStyle({
        greenLightColor: '#50514F',
        redLightColor: '#f25c5c',
      })
  }, [isItsTime])

  return (
    <svg
      width='339'
      height='1070'
      viewBox='0 0 339 1070'
      fill='none'
      xmlns='http://www.w3.org/2000/svg'
    >
      <g id='Group 14' filter='url(#filter0_d_901_10)'>
        <rect
          id='Rectangle 65'
          x='334'
          y='309'
          width='1'
          height='1'
          fill='#D9D9D9'
        />
        <rect
          id='Rectangle 66'
          x='4'
          width='296'
          height='561'
          rx='10'
          fill='#247BA0'
        />
        <rect
          id='Rectangle 67'
          x='133'
          y='560'
          width='38'
          height='502'
          fill='#50514F'
        />
        <circle
          id='green-light'
          cx='152'
          cy='104'
          r='81'
          fill={style.greenLightColor}
        />
        <circle
          id='red-light'
          cx='152'
          cy='454'
          r='81'
          fill={style.redLightColor}
        />
      </g>
      <defs>
        <filter
          id='filter0_d_901_10'
          x='0'
          y='0'
          width='339'
          height='1070'
          filterUnits='userSpaceOnUse'
          color-interpolation-filters='sRGB'
        >
          <feFlood flood-opacity='0' result='BackgroundImageFix' />
          <feColorMatrix
            in='SourceAlpha'
            type='matrix'
            values='0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 127 0'
            result='hardAlpha'
          />
          <feOffset dy='4' />
          <feGaussianBlur stdDeviation='2' />
          <feComposite in2='hardAlpha' operator='out' />
          <feColorMatrix
            type='matrix'
            values='0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0.25 0'
          />
          <feBlend
            mode='normal'
            in2='BackgroundImageFix'
            result='effect1_dropShadow_901_10'
          />
          <feBlend
            mode='normal'
            in='SourceGraphic'
            in2='effect1_dropShadow_901_10'
            result='shape'
          />
        </filter>
      </defs>
    </svg>
  )
}

export default Semaphore
