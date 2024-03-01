import React, { Component } from 'react';

export default class AddItemToInventory extends Component {
    submitItem(event) {
        event.preventDefault();
        let item = {
            name: this.refs.name.value,
            price: this.refs.price.value,
            type: this.refs.type.value,
        }

        fetch("http://localhost:8080/api/createNewItemAPI", {
            method: "POST",
            headers: {
                "content-type": "application/json",
            },
            body: JSON.stringify(item),
        })
        .then(response => response.json());
        window.location.reload();
    }

    render() {
        return (
            <div className="row">
                <form className="col s12" onSubmit={this.submitItem.bind(this)}>
                <div className="row">
                    <div className="input-field col s6">
                        <input ref="name" type="text" className="validate" />
                    	<label htmlFor="name">Name</label>
                    </div>
                    <div className="input-field col s6">
                        <input ref="price" type="text" className="striped" />
                        <label htmlFor="price">Price</label>
                    </div>
                    <div className="input-field col s3">
                        <input ref="type" type="text" className="validate" />
                        <label htmlFor="type">Type</label>
                    </div>
                </div>
                <div className="row">
                    <button className="waves-effect waves-light btn" type="submit" name="action">Submit</button>
                </div>
                </form>
            </div>
        )
    }
}