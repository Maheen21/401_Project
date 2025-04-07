import { Link } from "react-router-dom";

const Index = () => {
  return (
    <div className="w-full min-h-screen bg-white">
      {/* Hero Section */}
      <section className="flex flex-col lg:flex-row items-center justify-between px-10 py-24 max-w-7xl mx-auto">
        <div className="mb-10 lg:mb-0">
          <h1 className="text-5xl font-bold mb-4">DishCraft</h1>
          <p className="text-xl text-gray-600">Make Your Personalized Recipe</p>
        </div>
        <div className="flex gap-4">
          <Link to="/find">
            <button className="px-6 py-2 bg-gray-200 text-gray-700 rounded hover:bg-gray-300 transition">
              Find
            </button>
          </Link>
          <Link to="/explore">
            <button className="px-6 py-2 bg-black text-white rounded hover:bg-gray-900 transition">
              Explore
            </button>
          </Link>
        </div>
      </section>

      {/* Description Section */}
      <section className="relative w-full max-w-screen-xl px-6 py-16 flex justify-center items-center mx-auto">
        <div className="relative w-full  h-[400px]">
          <img
            src="https://images.pexels.com/photos/1279330/pexels-photo-1279330.jpeg"
            alt="Pasta"
            className="absolute inset-0 w-3/4  h-full object-cover"
          />
          {/* Gradient Overlay */}
          <div className="absolute inset-0 bg-gradient-to-r from-transparent via-white/100 to-white/100" />
          
          {/* Text box */}
          <div className="relative z-10 flex items-center justify-end h-full px-8">
          <div className="bg-white/80 rounded-lg p-6 max-w-md shadow-lg">
            <p className="text-2xl leading-relaxed text-gray-800 max-w-sm">
              <span className="font-bold text-black">DishCraft</span> is a personalized recipe maker that tailors cooking ideas to your preferences and lifestyle.
            </p>
            </div>
          </div>
        </div>
      </section>
    </div>
  );
};

export default Index;
