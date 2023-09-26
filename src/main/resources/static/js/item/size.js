function count(type)  {
  const resultElement = document.getElementById("size");

  let number = resultElement.value;

  if(type === 'plus') {
    number = parseInt(number) + 1;
  }else if(type === 'minus')  {
    if(parseInt(number)>1){
    number = parseInt(number) - 1;
    }
  }

  resultElement.value = number;
}