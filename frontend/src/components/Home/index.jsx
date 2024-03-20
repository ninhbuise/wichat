import React from 'react';
import { useLoaderData } from 'react-router-dom';

 const WelcomePage = () => {

    const dataLoading = useLoaderData();

    console.log(dataLoading, "dataLoading")
    

  return (
    <div className="min-h-screen relative flex flex-col justify-between items-between bg-gray-800">
      <div className="flex h-[500px] flex-col items-center justify-center py-16 z-20 "> 
      <div className="text-[96px] text-white font-bold">Welcome Back</div>
      </div>
      <video
        autoPlay
        loop
        muted
        className="w-full h-full object-cover z-0"
        src="https://baldursgate3.game/video.mp4" // Replace with your video path
      />
    </div>
  );
};

export default WelcomePage
