import React from "react";

type ButtonProps = {
  children: React.ReactNode;
  onClick?: () => void;
  type?: "button" | "submit" | "reset";
  variant?: "primary" | "outline";
  className?: string; // Tailwind custom className
};

const PrimaryButton = ({
  children,
  onClick,
  type = "button",
  variant = "primary",
  className = "",
}: ButtonProps) => {
  const baseStyle =
    "inline-flex items-center justify-center px-6 py-2 rounded-md text-base font-medium transition-colors duration-200";
  const primaryStyle = "bg-black text-white hover:bg-gray-800";
  const outlineStyle =
    "border border-gray-300 text-gray-800 bg-white hover:bg-gray-100";

  const finalClass =
    baseStyle +
    " " +
    (variant === "primary" ? primaryStyle : outlineStyle) +
    " " +
    className;

  return (
    <button type={type} onClick={onClick} className={finalClass}>
      {children}
    </button>
  );
};

export default PrimaryButton;
