import { useRouteError } from "react-router-dom";

export default function ErrorPage() {
  const error = useRouteError();

  return (
    <div
      id="error-page"
      className="w-[100vw] h-[100vh] flex justify-center flex-col items-center"
    >

        <img src="https://firebasestorage.googleapis.com/v0/b/static-only-2ff95.appspot.com/o/UI%2Ferror.jpeg?alt=media&token=42c578d4-adc1-4caa-a308-679a3c6c3610" className="w-full h-auto max-w-[800px]"  alt="" />
      <div className="flex flex-col justify-center items-center">
        <h1>Oops!</h1>
        <p>Sorry, an unexpected error has occurred.</p>
        <p>
          <i>{error.statusText || error.message}</i>
        </p>
      </div>
    </div>
  );
}
