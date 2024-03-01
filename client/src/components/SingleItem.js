import React from 'react';

const SingleItem = ({item}) => (
    <div className="row">
       <div className="col s6col s7 push-s5" align="left">
          <span>{item.name} {item.type}  {item.price}</span>
        </div>
    </div>
);

export default SingleItem;