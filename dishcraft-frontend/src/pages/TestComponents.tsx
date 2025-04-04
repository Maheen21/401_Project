import React from "react";
import PrimaryButton from "../components/PrimaryButton";

const TestComponents = () => {
  return (
    <div className="p-10 space-y-4">
      <h1 className="text-xl font-bold">버튼 테스트</h1>

      <PrimaryButton onClick={() => alert("Primary clicked!")}>
        Sign in
      </PrimaryButton>

      <PrimaryButton variant="outline" onClick={() => alert("Outline clicked!")}>
        Register
      </PrimaryButton>
    </div>
  );
};

export default TestComponents;
