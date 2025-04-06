interface ViolationMiniTagProps {
    label: string;
  }
  
  const ViolationMiniTag = ({ label }: ViolationMiniTagProps) => {
    return (
      <span className="text-[10px] px-2 py-0.5 rounded-full bg-red-500 text-white whitespace-nowrap">
        ⚠️ {label}
      </span>
    );
  };
  
  export default ViolationMiniTag;
  