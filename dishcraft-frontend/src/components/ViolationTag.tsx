interface ViolationTagProps {
    label: string;
  }
  
  const ViolationTag = ({ label }: ViolationTagProps) => {
    return (
      <span className="text-xs bg-red-500 text-white px-2 py-1 rounded-md uppercase tracking-wide">
        ⚠️ {label}
      </span>
    );
  };
  
  export default ViolationTag;
  