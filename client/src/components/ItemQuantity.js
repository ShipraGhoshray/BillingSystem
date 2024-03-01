import React, { Component } from 'react';
import SingleItem from './SingleItem';
import AddItemToInventory from './AddItemToInventory';

export default class ItemQuantity extends Component {
    constructor(props) {
        super(props);
        this.state = {
            itemQuantity: [], //load data from BE
        };
    }

    componentDidMount() {
        fetch('http://localhost:8080/api/itemInventoryApi')
        .then(response => response.json())
        .then(data => this.setState({itemQuantity: data}))
    }

    render() {
        return (
            <div>
            	<div className="row">
                    <p>Add item to inventory:</p>
                </div>
                <div className="row">
                    <AddItemToInventory />
                </div>
                <div className="row">
                    { this.state.itemQuantity.map((item) => (
                        <SingleItem key={item.id} item={item} />
                    ))}
                </div>
            </div>
        )
    }
}
